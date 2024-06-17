package ortus.boxlang.modules.jython.bifs;

import ortus.boxlang.modules.jython.JythonEngine;
import ortus.boxlang.runtime.bifs.BIF;
import ortus.boxlang.runtime.bifs.BoxBIF;
import ortus.boxlang.runtime.bifs.BoxMember;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.scopes.ArgumentsScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Argument;
import ortus.boxlang.runtime.types.BoxLangType;
import ortus.boxlang.runtime.types.IStruct;

@BoxBIF
@BoxMember( type = BoxLangType.STRING )
public class JythonEval extends BIF {

	private static final Key KeyScript = new Key( "script" );

	/**
	 * Constructor
	 */
	public JythonEval() {
		super();
		declaredArguments = new Argument[] {
		    new Argument( true, Argument.STRING, KeyScript )
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
		String script = arguments.getAsString( KeyScript );

		// Execute the script
		return JythonEngine.eval( context, script );
	}

}
