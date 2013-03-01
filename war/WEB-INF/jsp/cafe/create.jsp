<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="title_title" value="Cafe" />
<c:set var="page_title" value="Cafe" />
<%-- figure out a description --%>
<%@ include file="/WEB-INF/jsp/header.jspf" %>
<div class="page-header">
  <h5>Create <small>Cafe</small></h5>
</div>
<div class="section">
  <form:form method="post" action="/gym/save" modelAttribute="gym">
    <fieldset>
      <label>Name:</label>
      <form:input path="name" class="input-large" />
      <form:errors path="name" cssClass="help-inline alert-error" />
      <label>Time Zone:</label>
      <form:input path="timeZone" class="input-large" />
      <form:errors path="timeZone" cssClass="help-inline alert-error" />
      <div class="form-actions">
        <button type="submit" class="btn btn-primary">Create Gym</button>
        <button class="btn">Cancel</button>
      </div>
    </fieldset>
  </form:form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jspf" %>