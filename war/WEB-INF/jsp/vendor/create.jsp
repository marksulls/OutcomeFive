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
  <h4>New Vendor</h4>
</div>
<div class="section">
  <form:form method="post" action="/vendor/new" modelAttribute="vendor">
    <fieldset>
      <label>Name:</label>
      <form:input path="name" class="input-large required" />
      <form:errors path="name" cssClass="help-inline alert-error" />
      <label>Street 1:</label>
      <form:input path="street1" class="input-large" />
      <form:errors path="street1" cssClass="help-inline alert-error" />
      <label>Street 2:</label>
      <form:input path="street2" class="input-large" />
      <form:errors path="street2" cssClass="help-inline alert-error" />
      <label>Street 3:</label>
      <form:input path="street3" class="input-large" />
      <form:errors path="street3" cssClass="help-inline alert-error" />
      <label>City:</label>
      <form:input path="city" class="input-large" />
      <form:errors path="city" cssClass="help-inline alert-error" />
      <label>State:</label>
      <form:input path="state" class="input-large" />
      <form:errors path="state" cssClass="help-inline alert-error" />
      <label>Zip:</label>
      <form:input path="zip" class="input-large" />
      <form:errors path="zip" cssClass="help-inline alert-error" />
      <label>Phone:</label>
      <form:input path="phone" class="input-large" />
      <form:errors path="phone" cssClass="help-inline alert-error" />
      
      <div class="form-actions">
        <button type="submit" class="btn btn-primary">Create Vendor</button>
      </div>
    </fieldset>
  </form:form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jspf" %>