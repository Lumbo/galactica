package graphics;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Model {
	public List<Vector3f> vertices = new ArrayList<Vector3f>();
	public List<Vector3f> normals = new ArrayList<Vector3f>();
	public List<Face> faces = new ArrayList<Face>();
	
	private int vboVertexHandle;
	private int vboNormalHandle;
	
	public Model(){
		
	}
	
	public void done(){
		System.out.println(faces.size());
		FloatBuffer v = reserveData(faces.size()*9);
		FloatBuffer n = reserveData(faces.size()*9);
		for(Face face : faces){
			v.put(asFloats(vertices.get((int) face.vertex.x - 1)));
			v.put(asFloats(vertices.get((int) face.vertex.y - 1)));
			v.put(asFloats(vertices.get((int) face.vertex.z - 1)));
			
			n.put(asFloats(normals.get((int) face.normal.x - 1)));
			n.put(asFloats(normals.get((int) face.normal.y - 1)));
			n.put(asFloats(normals.get((int) face.normal.z - 1)));
		}
		
		v.flip();
		n.flip();
		
		vboVertexHandle = glGenBuffers();
		vboNormalHandle = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, v, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
		glBufferData(GL_ARRAY_BUFFER, n, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void draw(){
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glVertexPointer(3, GL_FLOAT, 0, 0L);
		glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
		glNormalPointer(GL_FLOAT, 0, 0L);
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		glColor3f(0.7f, 0.0f, 0.0f);
		glMaterialf(GL_FRONT, GL_SHININESS, 10f);
		glDrawArrays(GL_TRIANGLES, 0, faces.size()*3);
		
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private static float[] asFloats(Vector3f v){
		return new float[]{v.x, v.y, v.z};
	}
	private static FloatBuffer reserveData(int size){
		FloatBuffer data = BufferUtils.createFloatBuffer(size);
		return data;
	}
	
	//TODO cleanup code
}
