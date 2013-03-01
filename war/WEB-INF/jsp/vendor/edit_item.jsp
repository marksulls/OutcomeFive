<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="title_title" value="Gym" />
<c:set var="page_title" value="Gym" />
<%-- figure out a description --%>
<%@ include file="/WEB-INF/jsp/header.jspf" %>
<div class="page-header">
  ${item.id == 0 ? 'New Item for Vendor '.concat(vendor.name) : 'Update Item'}
  <small>@ ${_cafe.name}</small>
</div>
<div class="section">
  <form:form id="vendorItemForm" method="post" action="/vendor/${vendor.id}/item" modelAttribute="item">
    <fieldset>
      <div class="row-fluid">
        <div class="span3 right-border">
          <label>Name:</label>
          <form:input path="name" class="input-large required" />
          <form:errors path="name" cssClass="help-inline alert-error" />
          <label>Category:</label>
          <form:input path="category" class="input-large required" />
          <form:errors path="category" cssClass="help-inline alert-error" />
          <label>Description:</label>
          <form:textarea path="description" class="input-large" />
          <form:errors path="description" cssClass="help-inline alert-error" />
        </div>
        <div class="span3 right-border">
          <label>Cost:</label>
          <div class="input-prepend input-append">
            <span class="add-on">$</span>
            <form:input path="cost" class="input-mini required money" />
          </div>
          <form:errors path="cost" cssClass="help-inline alert-error" />
          <label>Item SKU / Code:</label>
          <form:input path="sku" class="input-large required" />
          <form:errors path="sku" cssClass="help-inline alert-error" />
          <label>UPC:</label>
          <form:input path="upc" class="input-large" />
          <form:errors path="upc" cssClass="help-inline alert-error" />
        </div>
        <div class="span6">
          <label>Measure Type:</label>
          <form:select path="measureType" class="required">
            <form:option value="">-- Select Type --</form:option>
            <form:option value="num">By Count</form:option>
            <form:option value="g">By Weight</form:option>
            <form:option value="ml">By Volume</form:option>
          </form:select>
          <form:errors path="measureType" cssClass="help-inline alert-error" />
          <label for="count">Count:</label>
          <form:input  path="count" class="input-mini number" />
          <form:errors path="count" cssClass="help-inline alert-error" />
          <label for="weight">Weight:</label>
          <div class="controls controls-row">
            <form:input  path="weight" class="span2 number" />
            <form:select  path="weightUnit" class="span2">
              <form:option value="lb">Pounds</form:option>
              <form:option value="oz">Ounces</form:option>
              <form:option value="g">Grams</form:option>
              <form:option value="kg">Kilograms</form:option>
            </form:select>
            <form:select path="weightPackage" class="span2">
              <form:option value="bag">Bag</form:option>
              <form:option value="box">Box</form:option>
              <form:option value="buschel">Buschel</form:option>
              <form:option value="brick">Brick</form:option>
              <form:option value="case">Case</form:option>
              <form:option value="sack">Sack</form:option>
            </form:select>
          </div>
          <form:errors path="weight" cssClass="help-inline alert-error" />
          <label for="weightLost">Scrap Lost:</label>
          <div class="controls controls-row">
            <form:input path="weightLost" class="span2 number" />
            <form:select path="weightLostUnit" class="span2">
              <form:option value="lb">Pounds</form:option>
              <form:option value="oz">Ounces</form:option>
              <form:option value="g">Grams</form:option>
              <form:option value="kg">Kilograms</form:option>
            </form:select>
          </div>
          <form:errors path="weightLost" cssClass="help-inline alert-error" />
          <label for="volume">Volume:</label>
          <div class="controls controls-row">
            <form:input path="volume" class="span2 number" />
            <form:select path="volumeUnit" class="span2">
              <form:option value="ML">Milliliters</form:option>
              <form:option value="L">Liters</form:option>
              <form:option value="floz">Fluid Ounces</form:option>
              <form:option value="gal">Gallons</form:option>
              <form:option value="cup">Cups</form:option>
              <form:option value="qt">Quart</form:option>
            </form:select>
            <form:select path="volumePackage" class="span2">
              <form:option value="can">Can</form:option>
              <form:option value="jar">Jar</form:option>
              <form:option value="jug">Jug</form:option>
            </form:select>
          </div>
          <form:errors path="volume" cssClass="help-inline alert-error" />
          <label for="volumeLost">Scrap Lost:</label>
          <div class="controls controls-row">
            <form:input path="volumeLost" class="span2 number" />
            <form:select path="volumeLostUnit" class="span2">
              <form:option value="ML">Milliliters</form:option>
              <form:option value="L">Liters</form:option>
              <form:option value="floz">Fluid Ounces</form:option>
              <form:option value="gal">Gallons</form:option>
              <form:option value="cup">Cups</form:option>
              <form:option value="qt">Quart</form:option>
            </form:select>
          </div>
          <form:errors path="volumeLost" cssClass="help-inline alert-error" />
        </div>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary">Save Item</button>
      </div>
    </fieldset>
  </form:form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jspf" %>