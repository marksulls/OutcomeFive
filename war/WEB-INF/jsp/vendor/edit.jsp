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
  <h4>
    ${vendor.id == 0 ? 'New Vendor' : 'Update Vendor'}
    <small>@ ${_cafe.name}</small>
  </h4>
</div>
<div class="section">
  <form:form method="post" action="/vendor" modelAttribute="vendor">
    <fieldset>
      <label>Name:</label>
      <form:input path="name" class="input-large required" />
      <form:errors path="name" cssClass="help-inline alert-error" />
      <label>Address:</label>
      <form:textarea path="address" class="input-large" />
      <form:errors path="address" cssClass="help-inline alert-error" />
      <%-- <div class="controls controls-row">
        <form:input path="city" class="span2" placeholder="City" />
        <form:select path="state" class="span1" placeholder="State"> 
          <form:option value="0" label="State" />
            <form:options items="${states}" itemValue="id" itemLabel="id" />
        </form:select>
        <form:input path="zip" class="span2" placeholder="Zip" />
      </div>
      <form:errors path="city" cssClass="help-inline alert-error" />
      <form:errors path="state" cssClass="help-inline alert-error" />
      <form:errors path="zip" cssClass="help-inline alert-error" /> --%>
      <label>Phone:</label>
      <form:input path="phone" class="input-large" />
      <form:errors path="phone" cssClass="help-inline alert-error" />
      <form:hidden path="id" />
      <div class="form-actions">
        <button type="submit" class="btn btn-primary">Create Vendor</button>
      </div>
    </fieldset>
  </form:form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jspf" %>