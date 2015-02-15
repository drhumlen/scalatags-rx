package scalatags.rx

import rx._
import utest._
import utest.framework.TestSuite

import scalatags.JsDom.all._
import scalatags.rx.RxDom._

object RxDomSuite extends TestSuite {

  def test[T](v: Var[T], f: => T, a: T, b: T): Unit = test(v, f, a, b -> b)

  def test[S, T](v: Var[S], f: => T, a: T, b: (S, T)): Unit = {
    assert(f == a)
    v() = b._1
    assert(f == b._2)
  }

  val tests = TestSuite {
    "Var[String] style" - {
      val c = Var("blue")
      val node = div(color := c).render
      test(c, node.style.getPropertyValue("color"), "blue", "green")
    }
    "Rx[String] style" - {
      val c = Var("blue")
      val node = div(color := (c: Rx[String])).render
      test(c, node.style.getPropertyValue("color"), "blue", "green")
    }
    "Var[String] pixel style" - {
      val c = Var("10px")
      val node = div(width := c).render
      test(c, node.style.getPropertyValue("width"), "10px", "20px")
    }
    "Rx[String] pixel style" - {
      val c = Var("10px")
      val node = div(width := (c: Rx[String])).render
      test(c, node.style.getPropertyValue("width"), "10px", "20px")
    }
    "Var[Int] pixel style" - {
      val c = Var(10)
      val node = div(width := c).render
      test(c, node.style.getPropertyValue("width"), "10px", 20 -> "20px")
    }
    "Rx[Int] pixel style" - {
      val c = Var(10)
      val node = div(width := (c: Rx[Int])).render
      test(c, node.style.getPropertyValue("width"), "10px", 20 -> "20px")
    }
    "Var[String] attribute" - {
      val c = Var("10px")
      val node = div(widthA := c).render
      test(c, node.getAttribute("width"), "10px", "20px")
    }
    "Rx[String] pixel style" - {
      val c = Var("10px")
      val node = div(widthA := (c: Rx[String])).render
      test(c, node.getAttribute("width"), "10px", "20px")
    }
    "Var[Int] attribute" - {
      val c = Var(10)
      val node = div(widthA := c).render
      test(c, node.getAttribute("width"), "10", 20 -> "20")
    }
    "Rx[Int] attribute" - {
      val c = Var(10)
      val node = div(widthA := (c: Rx[Int])).render
      test(c, node.getAttribute("width"), "10", 20 -> "20")
    }
  }
}