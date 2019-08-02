package examples

import chisel3._
import chisel3.util._



class PDM extends Module {
	
	val io = IO(new Bundle{
	   val inp = Input(SInt(8.W))  
	   val out = Output(Bool())
	   val qer = Output(SInt(8.W))
	})

 

io.out := false.B
io.qer := 0.S

 //val ireg = RegInit(0.S(8.W))



 val qreg  = RegInit(0.S(8.W))
 val oreg = RegInit(0.S(8.W))



 
  when(io.inp >= qreg){

  	io.out := true.B
  	oreg := 10.S
  	
    io.qer  :=  qreg

  }
  .elsewhen(io.inp < qreg){
  	 io.out := false.B
  	 oreg :=  0.S
  	 

  	 io.qer  := qreg
  }
   
 
  qreg := qreg + oreg - io.inp

  //qreg := qregpls1


}



