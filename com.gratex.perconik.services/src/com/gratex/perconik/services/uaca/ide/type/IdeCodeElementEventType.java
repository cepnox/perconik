package com.gratex.perconik.services.uaca.ide.type;

public enum IdeCodeElementEventType {

	VISIBLE_START("visiblestart"),
	VISIBLE_END("visibleend"),
	EDIT_START("editstart"),
	EDIT_END("editend");

    
    private final String urlPath;

    IdeCodeElementEventType(String urlPath) {
        this.urlPath = urlPath;
    }

    public String urlPath() {
        return urlPath;
    }

    public static IdeCodeElementEventType fromValue(String urlPath) {
        for (IdeCodeElementEventType item: IdeCodeElementEventType.values()) {
            if (item.urlPath.equals(urlPath)) {
                return item;
            }
        }
        throw new IllegalArgumentException(urlPath);
    }
}
