package com.retro.food.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.retro.core.data.Entity;
import com.retro.core.data.ObjectNotFoundException;
import com.retro.food.core.Cafe;

/**
 * operations related to cafes
 * @author mark
 */
@Controller
@RequestMapping("/cafe")
public class CafeController extends BaseController {
    // logging
    final Logger _log = LoggerFactory.getLogger(CafeController.class);
    // leader boards
    private final Pattern cafeIdPattern = Pattern.compile("^/cafe/(\\d+)/.*");
    
    @RequestMapping(value="switch")
    public String switchCafe(Model model) {
        // set the cafes for the given user
        model.addAttribute("cafes",getCurrentUser().getCafesOwned());
        return "cafe/set-cafe";
    }
    
    @RequestMapping(value="set",method = RequestMethod.POST)
    public String setCafe(@RequestParam(value = "cafeId") Long cafeId,
                    @RequestParam(value = "default", defaultValue = "0") boolean setAsDefault, Model model,HttpServletRequest request) {
        _log.debug("setting new cafe to [{}]",cafeId);
        // look up the cafe
        Cafe cafe = getCafeDao().getObjectById(cafeId);
        // sanity check
        if(cafe == null) {
            throw new ObjectNotFoundException("no cafe with id [" + cafeId + "]");
        }
        // send the user back where they came from
        String referrer = request.getHeader("referrer");
        _log.info("Referrer is [{}]", referrer);
        _log.info("Last requested page [{}]", request.getSession().getAttribute("lastHandledPage"));
        String lastHandledUri = (String) request.getSession().getAttribute("lastHandledPage");
        if (lastHandledUri != null) {
            // check for a cafe id
            Matcher cafeIdMatcher = cafeIdPattern.matcher(lastHandledUri);
            // find it
            if(cafeIdMatcher.find()) {
                // they were viewing a community specific page, redirect to the same page for the new community
                String newUri = lastHandledUri.replaceFirst("/\\d+/", "/" + Long.toString(cafeId) + "/");
                _log.info("New URI [{}]", newUri);
                return "redirect:" + newUri;
            } else {
                // redirect to the last handled URI, it's a report page
                return "redirect:" + lastHandledUri;
            }
        }
        // else just send home
        return "redirect:/";
    }
    
    @RequestMapping(value="/new")
    public String create(@PathVariable long cafeId,Model model) throws ObjectNotFoundException {
        // look up the cafe
        Cafe cafe = new Cafe();
        // else store it
        model.addAttribute("cafe",cafe);
        return "cafe/create";
    }
    
    @RequestMapping(value="/{cafeId}/edit")
    public String edit(@PathVariable long cafeId,Model model) throws ObjectNotFoundException {
        _log.debug("editing cafe [{}]",cafeId);
        // look up the cafe
        Cafe cafe = getCafeDao().getObjectById(cafeId);
        // sanity check
        if(cafe == null) {
            throw new ObjectNotFoundException("no cafe with id [" + cafeId + "]");
        }
        // else store it
        model.addAttribute("cafe",cafe);
        return "cafe/edit";
    }
    
    /**
     * updates an existing cafe after an edit
     */
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public String update(@Valid Cafe cafe,BindingResult result,Model model) throws Exception {
        _log.debug("updating cafe [{}]",cafe);
        if(result.hasErrors()) {
            _log.warn("validation failed with errors - [{}]",result.getAllErrors());
            addErrorMessage("Not all required fields were filled in");
            model.addAttribute("cafe",cafe);
            return "cafe/edit";  
        }
        // verify the primary key
        if(cafe.getId() == Entity.NULL) {
            _log.error("trying to update an object without a primary key",cafe);
            throw new Exception("trying to update an object without a primary key");
        }
        // should already be validated
        getCafeDao().saveOrUpdate(cafe);
        addSuccessMessage("Successfully updated cafe " + cafe.getName());
        return "redirect:/";
    }
}