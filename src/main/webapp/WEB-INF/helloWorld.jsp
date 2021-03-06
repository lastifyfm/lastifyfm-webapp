<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
</head>
<body>

<p>Lastify.Fm : 

<c:choose>
<c:when test="${securityLevel eq 'Protected'}" >
My music.
</c:when>
<c:when test="${securityLevel eq 'Public'}" >

Home. 
<p> <a href="/me">My music</a></p>
</c:when>
</c:choose>
</p>

                    <authz:authorize access="!hasRole('ROLE_USER')">
						<p>You are not logged in. &nbsp;<a href="/oauthlogin.jsp" />Login</a></p>
                    </authz:authorize>
                                  <authz:authorize access="hasRole('ROLE_USER')">
						You are logged in locally as <c:out value="${userName}" />. &nbsp;<a href="/logout">Logout</a></p>
						
                    </authz:authorize>
                      
                      <authz:authorize access="hasRole('ROLE_USER_TWITTER')">
					 <p>	You are connected with Twitter. </p>
                    </authz:authorize>
                      <authz:authorize access="hasRole('ROLE_USER_FACEBOOK')">
						<p>You are connected with Facebook. </p>
                    </authz:authorize>
       <authz:authorize access="hasRole('ROLE_USER_LASTFM')">
						<p>You are connected with LastFm. </p>
						<c:if test="${not empty lovedTracks}">
						
						<p>
						Your LastFM Loved Tracks :
						<select name="trackUrl">
						<c:forEach var="track" items="${lovedTracks}">
						<option value="<c:out value="${track.url}" />" /><c:out value="${track.name}" /></option>
						</c:forEach>
						</select>
						</p>
						</c:if>
						<c:if test="${not empty topTracks}">
					
						<p>
						Your LastFM Top Tracks :
						<select name="trackUrl">
						<c:forEach var="track" items="${topTracks}">
						<option value="<c:out value="${track.url}" />" /><c:out value="${track.name}" /></option>
						</c:forEach>
						</select>
						</p>
						</c:if>
						
                    </authz:authorize>
                           <authz:authorize access="hasRole('ROLE_USER_MIXCLOUD')">
						<p>You are connected with Mixcloud. </p>
                    </authz:authorize>
                     <authz:authorize access="hasRole('ROLE_USER_SOUNDCLOUD')">
						<p>You are connected with SoundCloud. </p>
											<c:if test="${not empty soundCloudFavorites}">
						<p>
						Your SoundCloud Favorites :
						<select name="trackUrl">
						<c:forEach var="track" items="${soundCloudFavorites}">
						<option value="<c:out value="${track.permalinkUrl}" />" /><c:out value="${track.title}" /></option>
						</c:forEach>
						</select>
						</p>
						</c:if>
                    </authz:authorize>
           
                    <authz:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_USER_FACEBOOK')">
						<p><a href="/oauthconnect.jsp">Connect</a> your account with Facebook</p>
                    </authz:authorize>
                    <authz:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_USER_TWITTER')">
						<p><a href="/oauthconnect.jsp">Connect</a> your account with Twitter</p>
                    </authz:authorize>
                               <authz:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_USER_LASTFM')">
						<p><a href="/oauthconnect.jsp">Connect</a> your account with LastFm</p>
                    </authz:authorize>
                                   <authz:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_USER_MIXCLOUD')">
						<p><a href="/oauthconnect.jsp">Connect</a> your account with Mixcloud</p>
                    </authz:authorize>
                                   <authz:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_USER_SOUNDCLOUD')">
						<p><a href="/oauthconnect.jsp">Connect</a> your account with Soundcloud</p>
                    </authz:authorize>
                       
                       
                       
                 
</body>                    
</html>