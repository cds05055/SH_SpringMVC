package kr.co.coupang.member.service;

import kr.co.coupang.member.domain.Member;

public interface MemberService {
	
	/**
	 * 멤버 등록 Service
	 * @param member
	 * @return int
	 */
	public int registerMember(Member member);
	
	/**
	 * 로그인 확인 Service
	 * @param member
	 * @return Member
	 */
	public Member memberLoginCheck(Member member);

	public Member selectOneById(String memberId);

	public int modifyMember(Member member);

	public int removeMember(String memberId);
}
