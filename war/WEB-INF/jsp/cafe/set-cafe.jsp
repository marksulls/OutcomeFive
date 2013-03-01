
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/tags/retro.tld" prefix="rc" %>
    <section class="wkMod wkMod_LoginForm" id="wkMod_LoginForm">
      <h2 class="wkMod-Title">Select a Gym</h2>
      <%-- <c:choose>
        <c:when test="${fn:length(gyms) > 0 || _athlete.super}">
          <form id="wkForm_SetGym" class="wkForm wkForm_SetGym wkStyle_Standard" action="/gym/set" method="POST">
            <p class="wkForm-Line">
              <rc:gymSelect />
            </p>
            <p>
              <input type="checkbox" name="default" value="1"/> Make this my default gym
            </p>
            <p class="wkForm-Line wkStyle_Right">
              <button type="submit" class="wkButton wkButton_Login wkStyle_Strong">Change Gym</button>
            </p>
          </form>
        </c:when>
        <c:otherwise>
          <p class="wkMod_SetGym-Description">
            Your account is not assigned to any gyms. If you feel this is an error please contact your manager.
          </p>
        </c:otherwise>
      </c:choose> --%>
    </section>
