package util;

public class Constant {
	
	public static class PhysicsConstant{
		private static final float toMeterConstant = 0.001f;
		
		public static float toMeter(float f){
			return f*toMeterConstant;
		}
	}
}
