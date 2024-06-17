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
package ortus.boxlang.modules.jython;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.IStruct;
import ortus.boxlang.runtime.types.Struct;
import ortus.boxlang.runtime.types.exceptions.BoxRuntimeException;
import ortus.boxlang.runtime.types.exceptions.ScopeNotFoundException;
import ortus.boxlang.runtime.util.FileSystemUtil;

public class JythonEngine {

	private static final ScriptEngineManager SCRIPT_MANAGER = new ScriptEngineManager();

	public static IStruct eval( IBoxContext context, String script ) {
		ScriptEngine	jython		= SCRIPT_MANAGER.getEngineByName( "jython" );

		// Bind the context variables scope into the script engine
		Bindings		bindings	= jython.createBindings();
		// Prep it with the variables scope
		context.getScopeNearby( Key.variables )
		    .entrySet()
		    .stream()
		    .forEach( entry -> bindings.put( entry.getKey().getName(), entry.getValue() ) );
		// Prep the return struct
		IStruct result = Struct.of();

		try {
			jython.setBindings( bindings, ScriptContext.ENGINE_SCOPE );
			jython.eval( script );

			result.put( "engine", jython );
			result.put( "globalScope", Struct.fromMap( jython.getBindings( ScriptContext.GLOBAL_SCOPE ) ) );
			result.put( "engineScope", Struct.fromMap( jython.getBindings( ScriptContext.ENGINE_SCOPE ) ) );
		} catch ( ScriptException e ) {
			throw new BoxRuntimeException( "Error executing Jython code " + e.getMessage(), e );
		}

		return result;
	}

	public static IStruct evalFile( IBoxContext context, String filepath ) {
		Path			absolutePath	= FileSystemUtil.expandPath( context, filepath ).absolutePath();
		File			targetFile		= absolutePath.toFile();
		ScriptEngine	jython			= SCRIPT_MANAGER.getEngineByName( "jython" );

		// If file doesn't exist throw an error
		if ( !targetFile.exists() ) {
			throw new BoxRuntimeException( "File not found: " + targetFile.getAbsolutePath() );
		}

		try ( FileReader reader = new FileReader( targetFile ) ) {
			// Bind the context variables scope into the script engine
			Bindings bindings = jython.createBindings();
			// Prep it with the variables scope
			context.getScopeNearby( Key.variables )
			    .entrySet()
			    .stream()
			    .forEach( entry -> bindings.put( entry.getKey().getName(), entry.getValue() ) );
			// Prep the return struct
			IStruct result = Struct.of();

			try {
				jython.setBindings( bindings, ScriptContext.ENGINE_SCOPE );
				jython.eval( reader );

				result.put( "engine", jython );
				result.put( "globalScope", Struct.fromMap( jython.getBindings( ScriptContext.GLOBAL_SCOPE ) ) );
				result.put( "engineScope", Struct.fromMap( jython.getBindings( ScriptContext.ENGINE_SCOPE ) ) );
			} catch ( ScriptException e ) {
				throw new BoxRuntimeException( "Error executing Jython code " + e.getMessage(), e );
			}

			return result;
		} catch ( ScopeNotFoundException | IOException e ) {
			throw new BoxRuntimeException( "Error reading python file " + e.getMessage(), e );
		}
	}

}
