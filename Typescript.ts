// pnpm add typescript --save-dev

// any unknown void object null undefined nuver
// unkonw: 未知类型, 不可使用属性 --> 强制 typeof 类型判断 才可使用

type AND = { name: string } & { age: number } 
type OR = { name: string } | { age: number }

interface User {
  // 默认修饰符为 public 
  readonly id: number 
  name: string 
  [key: string]: any 
}

abstract class Abc {
  abstract abstractMethod: void 
}

class Person implements User {

  constructor(
    public readonly id: number,
    public name: string,
    protected age: number = 0, // 当前类和子类 
    private secret: string = ""
  ) { /* 自动挂载 */ }
}

function func<T = string>(arg: T): T {
  return arg as T 
}

interface HasLength<T> {
  length: T 
}

class Stack<T> {
  // pass 
}

// ? extends Object / ? super Object


