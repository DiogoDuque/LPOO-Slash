package com.lpoo.slash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lpoo.slash.Slash;

import static com.lpoo.slash.Slash.getFilesDir;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//DESKTOP IS NOT USED!!
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Slash(getFilesDir()), config);
	}
}
