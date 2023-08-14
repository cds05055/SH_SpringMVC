package kr.co.coupang.member.service;

import kr.co.coupang.member.domain.Member;

public interface MemberService {
	
	/**
	 * ��� ��� Service
	 * @param member
	 * @return int
	 */
	public int registerMember(Member member);
	
	/**
	 * �α��� Ȯ�� Service
	 * @param member
	 * @return Member
	 */
	public Member memberLoginCheck(Member member);

	public Member selectOneById(String memberId);

	public int modifyMember(Member member);

	public int removeMember(String memberId);
}
