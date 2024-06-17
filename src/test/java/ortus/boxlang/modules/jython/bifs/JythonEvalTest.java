package ortus.boxlang.modules.jython.bifs;

import static com.google.common.truth.Truth.assertThat;

import javax.script.ScriptEngine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ortus.boxlang.runtime.BoxRuntime;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.context.ScriptingRequestBoxContext;
import ortus.boxlang.runtime.scopes.IScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.scopes.VariablesScope;
import ortus.boxlang.runtime.types.IStruct;

public class JythonEvalTest {

	static BoxRuntime	instance;
	IBoxContext			context;
	IScope				variables;
	static Key			result	= new Key( "result" );

	@BeforeAll
	public static void setUp() {
		instance = BoxRuntime.getInstance( true );
	}

	@BeforeEach
	public void setupEach() {
		context		= new ScriptingRequestBoxContext( instance.getRuntimeContext() );
		variables	= context.getScopeNearby( VariablesScope.name );
	}

	@DisplayName( "It can test the jython BIF" )
	@Test
	public void testExampleBIF() {
		// @formatter:off
		instance.executeSource( """
			result = JythonEval( "area = 17.68" )
		""", context );
		// @formatter:on
		IStruct			result	= variables.getAsStruct( Key.result );
		ScriptEngine	engine	= ( ScriptEngine ) result.get( "engine" );
		assertThat( engine.get( "area" ) ).isEqualTo( 17.68 );
	}

}
