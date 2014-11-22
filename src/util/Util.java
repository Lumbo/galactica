package util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Util {
	public static FloatBuffer asFloatBuffer(float [] floats){
		FloatBuffer res = BufferUtils.createFloatBuffer(floats.length);
		res.put(floats);
		res.flip();
		return res;
	}
}
