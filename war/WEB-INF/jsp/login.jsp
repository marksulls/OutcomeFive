<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="hide_nav" value="true" />
<%@ include file="/WEB-INF/jsp/header.jspf" %>
<%-- <p>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p> --%>
  <form class="form-signin" action="/j_spring_security_check" method="post">
    <h2 class="form-signin-heading">Please sign in</h2>
    <input name="j_username" class="input-block-level" type="text" placeholder="Email" >
    <input name="j_password" class="input-block-level" type="password" placeholder="Password">
    <button class="btn btn-large btn-primary pull-right" type="submit">Sign in</button>
    <label class="checkbox">
      <input type="checkbox" name="_spring_security_remember_me" value="remember_me"> Remember me
    </label>
    <br />
    <div>
      <p>Haven't registered? <a href="/register">Sign Up!</a></p>
    </div>
  </form>
<%@ include file="/WEB-INF/jsp/footer.jspf" %>