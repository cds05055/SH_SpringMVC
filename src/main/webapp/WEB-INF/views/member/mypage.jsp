<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>마이페이지</title>
	</head>
	<body>
		<h1>${member.memberName }님 마이페이지</h1>
			<fieldset>
				<legend>개인정보 수정</legend>
				<ul>
					<li>
						<label>아이디</label>
						<span>${memberId}</span>
					</li>
					<li>
						<label>이름</label>
						<span>${member.memberName}</span>
					</li>
					<li>
						<label>나이</label>
						<span>${member.memberAge}</span>
					</li>
					<li>
						<label>성별</label>
						<c:if test="${member.memberGender eq 'M' }">
							<span>남성</span>
						</c:if>
						<c:if test="${member.memberGender eq 'F' }">
							<span>여성</span>
						</c:if>
					</li>
					<li>
						<label>이메일</label>
						<span>${member.memberEmail}</span>
					</li>
					<li>
						<label>전화번호</label>
						<span>${member.memberPhone}</span>
					</li>
					<li>
						<label>주소</label>
						<span>${member.memberAddress}</span>
					</li>
					<li>
						<label>취미<label>
						<span>${member.memberHobby}</span>
					</li>
				</ul>			
			</fieldset>
			<br>
			<a href="/index.jsp">메인으로 이동</a>
			<a href="/member/modify.do?memberId=${member.memberId }">수정하기</a>
			<a href="/member/delete.do?memberId=${member.memberId }">삭제하기</a>
			<a href="/member/allMembers.do">전체 회원 조회</a>
	</body>
</html>