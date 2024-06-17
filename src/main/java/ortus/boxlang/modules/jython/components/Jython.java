/**
 * [BoxLang]
 *
 * Copyright [2023] [Ortus Solutions, Corp]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ortus.boxlang.modules.jython.components;

import java.util.Set;

import ortus.boxlang.modules.jython.JythonEngine;
import ortus.boxlang.runtime.components.Attribute;
import ortus.boxlang.runtime.components.BoxComponent;
import ortus.boxlang.runtime.components.Component;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.dynamic.ExpressionInterpreter;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.IStruct;
import ortus.boxlang.runtime.validation.Validator;

@BoxComponent( allowsBody = true, requiresBody = true )
public class Jython extends Component {

	public Jython() {
		super();
		declaredAttributes = new Attribute[] {
		    new Attribute( Key.variable, "string", Set.of( Validator.REQUIRED, Validator.NON_EMPTY ) )
		};
	}

	/**
	 * A component to execute python code in the Jython engine
	 *
	 * @param context        The context in which the Component is being invoked
	 * @param attributes     The attributes to the Component
	 * @param body           The body of the Component
	 * @param executionState The execution state of the Component
	 *
	 * @attribute.variable The variable to store the result in
	 *
	 * @return The result is a struct of: <code>engine, globalScope, engineScope</code>
	 */
	public BodyResult _invoke( IBoxContext context, IStruct attributes, ComponentBody body, IStruct executionState ) {
		StringBuffer	buffer		= new StringBuffer();
		BodyResult		bodyResult	= processBody( context, body, buffer );

		// IF there was a return statement inside our body, we early exit now
		if ( bodyResult.isEarlyExit() ) {
			// A return statement inside of savecontent discards all output that was built up
			return bodyResult;
		}
		// Get the content of the body
		String	content			= buffer.toString();
		String	variableName	= attributes.getAsString( Key.variable );

		// Execute the script
		IStruct	result			= JythonEngine.eval( context, content );

		// Set the result back into the page
		ExpressionInterpreter.setVariable( context, variableName, result );
		return DEFAULT_RETURN;
	}

}
