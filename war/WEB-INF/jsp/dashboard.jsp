<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="page_title" value="${cafe.name}" />
<%@ include file="/WEB-INF/jsp/header.jspf" %> 
<div class="page-header">
  <h4>Dashboard <small>@ ${_cafe.name}</small></h4>
</div>
<div class="row-fluid">
  <div class="span6">
    <div class="page-header">
      <div class="buttons btn-group pull-right">
        <a href="/menu" class="btn btn-mini tip-bottom" title="New Menu"><i class="icon-plus"></i> New Menu</a>
      </div>
      <h5>Menus</h5>
    </div>
    <div class="section">
      <c:choose>
        <c:when test="${menus == null || empty menus}">
          <div>
            <p>No menus. <a href="/menu">Create one</a></p>
          </div>
        </c:when>
        <c:otherwise>
          <table class="table table-bordered table-striped table-condensed">
            <tbody>
              <c:forEach var="menu" items="${menus}">
                <tr>
                  <td><a href="/menu/${menu.id}">${menu.name}</a></td>
                  <td style="width:40px">
                    <div class="btn-group">
                      <button class="btn dropdown-toggle btn-mini" data-toggle="dropdown">
                        <i class="icon-cog icon"></i>
                      </button>
                      <ul class="dropdown-menu pull-right">
                        <li><a href="/menu/${menu.id}"><i class="icon-edit"></i> View Menu</a></li>
                        <li><a href="/menu/${menu.id}/edit"><i class="icon-edit"></i> Edit Menu Items</a></li>
                        <li><a href="/menu/${menu.id}/item"><i class="icon-edit"></i> Add Menu Item</a></li>
                        <li class="divider"></li>
                        <li><a href="/menu/${menu.id}/delete"><i class="icon-trash"></i> Delete Menu</a></li>
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
  <div class="span6">
    <div class="page-header">
      <div class="buttons btn-group pull-right">
        <a href="/vendor" class="btn btn-mini tip-bottom" title="New Vendor"><i class="icon-plus"></i> New Vendor</a>
      </div>
      <h5>Vendors</h5>
    </div>
    <div class="section">
      <c:choose>
        <c:when test="${vendors == null || empty vendors}">
          <div>
            <p>No vendors. <a href="/vendor">Create one</a></p>
          </div>
        </c:when>
        <c:otherwise>
          <table class="table table-bordered table-striped table-condensed">
            <tbody>
              <c:forEach var="vendor" items="${vendors}">
                <tr>
                  <td><a href="/vendor/${vendor.id}">${vendor.name}</a></td>
                  <td style="width:40px">
                    <div class="btn-group">
                      <button class="btn dropdown-toggle btn-mini" data-toggle="dropdown">
                        <i class="icon-cog icon"></i>
                      </button>
                      <ul class="dropdown-menu pull-right">
                        <li><a href="/vendor/${vendor.id}/items"><i class="icon-edit"></i> View Items from Vendor</a></li>
                        <li><a href="/vendor/${vendor.id}/item"><i class="icon-plus"></i> Add Item from Vendor</a></li>
                        <li><a href="/vendor/${vendor.id}/edit"><i class="icon-edit"></i> Edit Vendor</a></li>
                        <li class="divider"></li>
                        <li><a href="/vendor/${vendor.id}/delete"><i class="icon-trash"></i> Delete Vendor</a></li>
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