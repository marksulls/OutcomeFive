<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="page_title" value="${cafe.name}" />
<%@ include file="/WEB-INF/jsp/header.jspf" %> 
<div class="page-header">
  <h4>${cafe.name} <small>Dashboard</small></h4>
</div>
<div class="row-fluid">
  <div class="span6">
    <div class="page-header">
      <h5>Menus</h5>
    </div>
    <div class="section">
      <c:choose>
        <c:when test="${menus == null || empty menus}">
          <div>
            <p>No menus. <a href="/menu/new">Create one</a></p>
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
                        <li><a href="/menu/${menu.id}/edit"><i class="icon-edit"></i> Edit Menu Items</a></li>
                        <li class="divider"></li>
                        <li><a href="/menu/${menu.id}"><i class="icon-trash"></i> Delete Menu</a></li>
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
      <h5>Vendors</h5>
    </div>
    <div class="section">
      <c:choose>
        <c:when test="${vendors == null || empty vendors}">
          <div>
            <p>No vendors. <a href="/vendor/new">Create one</a></p>
          </div>
        </c:when>
        <c:otherwise>
          <table class="table table-bordered table-striped table-condensed">
            <tbody>
              <c:forEach var="vendor" items="${vendors}">
                <tr>
                    <td>${vendor.name}</td>
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