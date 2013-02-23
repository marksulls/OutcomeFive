<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
  $(document).ready(function() {
    $("#passwordForm").validate({
        rules: {
            password: {
              required: true,
            },
            confirm: {
              equalTo: "#password"
            },
        },
        highlight: function(label) {
          $(label).closest('.control-group').addClass('error');
        },
        success: function(label) {
          label
            .text('OK!').addClass('valid')
            .closest('.control-group').addClass('success');
        }
    });
  });
</script>
<form:form id="passwordForm" method="post" action="/profile/password" modelAttribute="pModel">
  <fieldset>
    <div class="control-group">
      <label>Password:</label>
      <form:password id="password" path="password" class="text" />
      <form:errors path="password" />
    </div>
    <div class="control-group">
      <label>Confirm Password:</label>
      <form:password path="confirm" class="text" />
      <form:errors path="confirm" />
    </div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Change Password</button>
    </div>
    <form:hidden path="athleteId" />
  </fieldset>
</form:form>
