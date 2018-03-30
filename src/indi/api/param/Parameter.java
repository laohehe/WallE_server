package indi.api.param;

public class Parameter {
	/*
	 * 8,电台
	 * datelist,按照日期选择内容
	 */
	private static String[] apiType = new String[]{"0", 
		"1", "2", "3", "4", "5", "idlist","onelist","8","datelist"};

	public static String[] getApiType() {
		return apiType;
	}

	public static void setApiType(String[] apiType) {
		Parameter.apiType = apiType;
	}
	
}
