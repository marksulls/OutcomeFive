<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="page_title" value="${vendor.name}" />
<%@ include file="/WEB-INF/jsp/header.jspf" %> 
<div class="page-header">
  <h4>${vendor.name} <small>@ ${_cafe.name}</small></h4>
</div>
<div class="row-fluid">
  <div class="span6">
    <div class="page-header">
      <div class="buttons btn-group pull-right">
        <a href="/vendor/${vendor.id}/edit" class="btn btn-mini tip-bottom" title="New Menu"><i class="icon-plus"></i> Edit Vendor</a>
      </div>
      <h5>Information</h5>
    </div>
    <div class="section">
      <address>
        <strong>${vendor.name}</strong>
        <br>${vendor.address}
     <%--    <br>${vendor.street2}
        <br>${vendor.street3}
        <br>${vendor.city}, ${vendor.state} ${vendor.zip}<br> --%>
        <br>Phone: ${vendor.phone}
      </address>
    </div>
  </div>
  <div class="span6">
    <div class="page-header">
      <div class="buttons btn-group pull-right">
        <a href="/vendor" class="btn btn-mini tip-bottom" title="New Vendor"><i class="icon-plus"></i> New Vendor</a>
      </div>
      <h5>Items from this vendor</h5>
    </div>
    <div class="section">
      <c:choose>
        <c:when test="${items == null || empty items}">
          <div>
            <p>No items from this vendor. <a href="/vendor/${vendor.id}/item">Create one</a></p>
          </div>
        </c:when>
        <c:otherwise>
          <table class="table table-bordered table-striped table-condensed">
            <tbody>
              <c:forEach var="item" items="${items}">
                <tr>
                  <td><a href="/vendor/item/${item.id}">${item.name}</a></td>
                  <td style="width:40px">
                    <div class="btn-group">
                      <button class="btn dropdown-toggle btn-mini" data-toggle="dropdown">
                        <i class="icon-cog icon"></i>
                      </button>
                      <ul class="dropdown-menu pull-right">
                        <li><a href="/vendor/${vendor.id}/item/${item.id}/edit"><i class="icon-edit"></i> Edit Item</a></li>
                        <li class="divider"></li>
                        <li><a href="/vendor/${vendor.id}/item/${item.id}/delete"><i class="icon-trash"></i> Delete Item</a></li>
                      </ul>
                    </div>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/jsp/footer.jspf" %>