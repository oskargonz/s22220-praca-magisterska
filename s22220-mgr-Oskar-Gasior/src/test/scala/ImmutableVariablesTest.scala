import best_practices.ImmutableVariables
import org.scalatest.funsuite.AnyFunSuite

class ImmutableVariablesTest extends AnyFunSuite {
  test("ImmutableVariablesTest") {
    assert(ImmutableVariables.foo(10) === 10)
  }
}
