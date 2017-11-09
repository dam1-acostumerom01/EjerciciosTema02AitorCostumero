package Ejercicio02;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MiObjectOutputStream extends ObjectOutputStream{

	
	public MiObjectOutputStream(OutputStream out) throws IOException {
		super(out);
		// TODO Auto-generated constructor stub
	}
	
	public MiObjectOutputStream() throws IOException, SecurityException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		//no hacer nada
	}

	
	
}
