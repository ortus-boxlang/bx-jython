package ortus.boxlang.modules.jython.bifs;

import ortus.boxlang.modules.jython.JythonEngine;
import ortus.boxlang.runtime.bifs.BIF;
import ortus.boxlang.runtime.bifs.BoxBIF;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.scopes.ArgumentsScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Argument;
import ortus.boxlang.runtime.types.IStruct;

@BoxBIF
public class JythonEvalFile extends BIF {

	/**
	 * Constructor
	 */
	public JythonEvalFile() {
		super();
		declaredArguments = new Argument[] {
		    new Argument( true, Argument.STRING, Key.path )
		};
	}

	/**
	 * Executes the Jython script with the variables in the context.
	 *
	 * @param context   The context in which the BIF is being invoked.
	 * @param arguments Argument scope for the BIF.
	 *
	 * @argument.script The Jython script to execute.
	 *
	 * @return The result is a struct of: <code>engine, globalScope, engineScope</code>
	 */
	public IStruct _invoke( IBoxContext context, ArgumentsScope arguments ) {
		// Get the content of the body
		String filePath = arguments.getAsString( Key.path );

		// Execute the script
		return JythonEngine.evalFile( context, filePath );
	}

}
