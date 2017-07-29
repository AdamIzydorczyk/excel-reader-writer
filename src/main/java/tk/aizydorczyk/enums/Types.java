package tk.aizydorczyk.enums;

public enum Types {

	LIST("java.util.List");

	private String type;

	Types(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
