// See LICENSE.txt for license details.
package examples

<<<<<<< HEAD
assssssssssdsdsadddddddddadasdasdasdasdasdda



=======
>>>>>>> bf51cdd38dd2c430305eca8514c6bd1a431258dd
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}


import java.io.File

import chisel3._
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{FlatSpec, Matchers}



class AdderTests(c: Adder) extends PeekPokeTester(c) {
  for (t <- 0 until (1 << (c.n + 1))) {
    val rnd0 = rnd.nextInt(1 << c.n)
    val rnd1 = rnd.nextInt(1 << c.n)
    val rnd2 = rnd.nextInt(2)

    poke(c.io.A, rnd0)
    poke(c.io.B, rnd1)
    poke(c.io.Cin, rnd2)
    step(1)
    val rsum = rnd0 + rnd1 + rnd2
    val mask = BigInt("1"*c.n, 2)

    expect(c.io.Sum, rsum &  mask)
    expect(c.io.Cout,  ((1 << c.n) & rsum) >> c.n)
  }
}


/*

class AdderTester extends ChiselFlatSpec {
  behavior of "Adder"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers $backend" in {
      Driver(() => new Adder(8))(c => new AdderTests(c)) should be (true)
    }
  }
}

*/




class AdderTester extends ChiselFlatSpec{
   behavior of "Adder"
   


   it should "run verilator via command line arguments" in {                           // class for verilator
    // val args = Array.empty[String]
    val args = Array("--backend-name", "verilator")
    iotesters.Driver.execute(args, () => new Adder(8)) { c =>
      new AdderTests(c)
    } should be (true)
  }
}



