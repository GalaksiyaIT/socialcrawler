package com.galaksiya.social.entity;

public class FamilyBond {
	private String profileId;
	private String userId;
	private String name;
	private String birthDay;
	private String relationship;

	public FamilyBond() {
		super();
	}

	public FamilyBond(String profileId, String userId, String name,
			String birthDay, String relationship) {
		super();
		this.profileId = profileId;
		this.userId = userId;
		this.name = name;
		this.birthDay = birthDay;
		this.relationship = relationship;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String uId) {
		this.userId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public static enum RELATIONSHIPS {
		husband, brother, father, son, uncle, nephew, grandfather, grandson, wife, sister, mother, daughter, aunt, niece, grandmother, granddaughter, partner, cousin
	}

}
