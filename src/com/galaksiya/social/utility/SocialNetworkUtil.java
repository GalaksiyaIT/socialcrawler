package com.galaksiya.social.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SocialNetworkUtil {
	/**
	 * this method splits given permission string according to ',' operator and
	 * adds permissons to the permission list
	 * 
	 * @return
	 */
	public static List<String> generatePermissionList(String permissions) {
		List<String> permissionList = new ArrayList<String>();
		if (permissions != null) {
			StringTokenizer tokenizer = new StringTokenizer(permissions, ",");
			while (tokenizer.hasMoreElements()) {
				String element = (String) tokenizer.nextElement();
				permissionList.add(element.trim());
			}
		}
		return permissionList;
	}

	/**
	 * This method checks if requested permissions is contained by the permisson
	 * list
	 * 
	 * @param permissionList
	 *            TODO
	 * @param permission
	 * 
	 * @return
	 */
	public static boolean isRetrievable(List<String> permissionList, String permission) {
		if (permissionList.isEmpty() || permission == null
				|| permission.equals("") || permissionList.contains(permission)) {
			return true;
		}
		return false;
	}
}
