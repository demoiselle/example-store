package org.demoiselle.jee7.entity

Integer prioridade = 0
String  nome
Object  when
Object  then

def static create(closure) {
    RegraDsl regraDsl = new RegraDsl()
    closure.delegate = regraDsl
    closure()
}

def prioridade(Integer p)
{   if(p)
     this.prioridade = p
    else
       prioridade = 0
}
def nome(String toText) {
    this.nome = toText
}

def when(Object obj) {
	 this.when = obj
}

def then(Object obj) {
	this.then= obj	   
}
 