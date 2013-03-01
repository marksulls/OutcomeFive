<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="title_title" value="Registration" />
<c:set var="page_title" value="Registration" />
<%@ include file="/WEB-INF/jsp/header.jspf" %>
<div class="page-header">
  <h5>Create your Account</h5>
</div>
<div class="section">
  <form:form id="registrationForm" method="post" action="/register" modelAttribute="registration">
    <fieldset>
      <label>Name:</label>
      <form:input path="name" class="input-large required" />
      <form:errors path="name" cssClass="help-inline alert-error" />
      <label>Email:</label>
      <form:input path="email" class="input-large required email" />
      <form:errors path="email" cssClass="help-inline alert-error" />
      <div class="control-group">
        <label>Password:</label>
        <form:password id="password" path="password" class="text required" />
        <form:errors path="password" cssClass="help-inline alert-error" />
      </div>
      <div class="control-group">
        <label>Confirm Password:</label>
        <form:password path="confirm" class="text required" />
        <form:errors path="confirm" cssClass="help-inline alert-error" />
      </div>
      <label>Cafe Name:</label>
      <form:input path="cafeName" class="input-large required" />
      <form:errors path="cafeName" cssClass="help-inline alert-error" />
        <div class="form-actions">
          <button type="submit" class="btn btn-primary">Register</button>
        </div>
    </fieldset>
  </form:form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jspf" %>