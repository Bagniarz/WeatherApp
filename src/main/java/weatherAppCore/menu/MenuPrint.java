package weatherAppCore.menu;

import lombok.Data;

public @Data class MenuPrint {
    private String string;

    public MenuPrint() {}

    public MenuPrint(String string) {
        this.string = string;
    }

    public void print() {
        System.out.println(string);
    }

    public void clear() {
        setString(null);
    }
}
