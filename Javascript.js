// nvm 管理 node
// npm install -g pnpm
// pnpm install
// pnpm add dependency@version --save-dev
// pnpm config get registry
// pnpm config set registry https://registry.npmmirror.com/

const str = String(0).trim()
const arr = [1, 2, 3]
str.length 
arr.length 

arr.map((val, idx) => {})
arr.filter((val, idx) => {})
arr.find((val, idx) => {})
arr.includes(val) 

// pop去尾 push入尾 shift删头 unshift添头 slice(start, end) splice(start, deleteCount, ...items)

class User extends Object {
  constructor(name) {
    super()
    this.name = name 
  }

  useSuperMethod() {
    super.superMethod()
  }
}

const obj = new User('name')

for (const key in obj) {}
for (const ch of str) {}

// ? : `${}` typeof ...var [key] instanceof 

let iter = array[Symbol.iterator]() 
iter.next() // { value: 1, done: false }

console.log()

// JS 对于不存在的字段默认认定为 undefined
// number string boolean symbol object null undefined
// Number String Boolean Object

// 异步 -- async await 语法糖 
const promise = new Promise((resolve, reject) => {
  if (true) {
    resolve(data)
  } else {
    reject(new Error('error'))
  }
})

promise.then((data) => {
  // 行为 
}, (e) => {
  // 错误处理
})


function* generator() {
  yield 0 
}

const iterator = generator()
iterator.next()

// setTimeout setInterval clearTimeout clearInterval 
dispatchEvent(new CustomEvent('eventName', {
  detail: data 
}))

handler = (event) => {
  const res = event.detail 
}

addEventListener("evnetName", handler)
removeEventListener("eventName", handler)

/**
 * 事件 
 * @click @dbclick @click.right 
 * @mousedown @mouseup @resize 
 * @mouseenter @mouseleave @mousemove 
 * @input @focus @keydown="handleKeyDown"
 * @keyup.enter @keyup.esc @change 
 * @keyup.up @keyup.down @keyup.left @keyup.right 
 * @contextmenu.prevent.stop = "handleContextMenu"
 */

const handleKeyDown = (event) => {
  if (event.key == "F12") {
    event.preventDefault()
    event.stopPropagation()
  }
}

document.cookie = "key=val; key=val" 
document.documentElement 
document.querySelector() 
document.querySelectorAll() 
object.getAttribute() 
object.setAttribute(attr, val) 

JSON.stringify(obj)
JSON.parse(str)

// localStorage / sessionStorage
// getItem(key) setItem(key, val) removeItem(key) clear()

window.scrollTo(x, y)

// encodeURIComponent(str) decodeURIComponent(encodedStr)

const now = new Date()
// getFullYear() getMonth() getDate() 
// getHours() getMinutes() getSeconds()

try {} catch (e) {} finally {}

// Map 
const map = new Map() 
map.has("key") 
map.set("key", "value") 
map.get("key") 
map.delete("key")
map.clear() 
map.size 

// Set 
const set = new Set() 
Array.from(set) 
set.add("value")
set.delete("value") 
set.size 

// Reflect 
const user = { age: 20 } 
Reflect.has(user, "age") 
Reflect.get(user, "age", receiver) 
Reflect.set(user, "age", 22, receiver) 
Reflect.deleteProperty(user, "age")

// Proxy 
const proxy_obj = { name: "name", age: "age" }
const proxy = new Proxy(proxy_obj, {
  get(target, attr, receiver) {
    return Reflect.get(target, attr, receiver)
  },

  set(target, attr, value, receiver) {
    return Reflect.set(target, attr, value, receiver)
  }
})


/* IndexDB */
const DB_NAME = "dbName"
const DB_VERSION = 1
const STORE_NAME = "documents"
const DOC_KEY = "currentDoc"

// 打开数据库, 返回 IDBDatabase 
function openDB() {
  return new Promise((resolve, reject) => {
    const req = indexedDB.open(DB_NAME, DB_VERSION)

    // 首次创建 / 版本升级时建表
    req.onupgradeneeded = () => {
      const db = req.result
      if (!db.objectStoreNames.contains(STORE_NAME)) {
        db.createObjectStore(STORE_NAME, { keyPath: "id" })
      }
    }

    req.onsuccess = () => resolve(req.result)
    req.onerror = () => reject(req.error)
    req.onblocked = () => reject(new Error("IndexedDB upgrade blocked"))
  })
}

// 保存 (新增或覆盖) 一条文档记录 
export async function saveToDB(json, html) {
  const db = await openDB()
  return new Promise((resolve, reject) => {
    const tx = db.transaction(STORE_NAME, "readwrite")
    const record = {
      id: DOC_KEY,
      json,
      html,
      updatedAt: new Date().toISOString(),
    }

    tx.objectStore(STORE_NAME).put(record)

    tx.oncomplete = () => {
      db.close()
      resolve()
    }
    tx.onerror = () => {
      db.close()
      reject(tx.error)
    }
    tx.onabort = () => {
      db.close()
      reject(tx.error ?? new Error("Transaction aborted"))
    }
  })
}

// 读取当前文档, 没有则返回 null 
export async function loadFromDB() {
  const db = await openDB()
  return new Promise((resolve, reject) => {
    const tx = db.transaction(STORE_NAME, "readonly")
    const req = tx.objectStore(STORE_NAME).get(DOC_KEY)

    req.onsuccess = () => {
      db.close()
      resolve(req.result ?? null)
    }
    req.onerror = () => {
      db.close()
      reject(req.error)
    }
  })
}

// 删除当前文档 
export async function deleteFromDB() {
  const db = await openDB()
  return new Promise((resolve, reject) => {
    const tx = db.transaction(STORE_NAME, "readwrite")
    tx.objectStore(STORE_NAME).delete(DOC_KEY)

    tx.oncomplete = () => {
      db.close()
      resolve()
    }

    tx.onerror = () => {
      db.close()
      reject(tx.error)
    }

    tx.onabort = () => {
      db.close()
      reject(tx.error ?? new Error("Transaction aborted"))
    }
  })
}


/* Axios */
import axios from "axios"

const myAxios = axios.create({
  // 开发环境走 Vue 代理 (同源 /api), 避免跨域丢 Cookie
  baseURL:
    process.env.NODE_ENV === "development" ? "" : "https://codefather.cn",
  timeout: 10000,
  withCredentials: true,
})

myAxios.interceptors.request.use(
  function (config) {
    return config
  },
  function (error) {
    return Promise.reject(error)
  }
)


myAxios.interceptors.response.use(
  function (response) {

    console.log(response)

    const { data } = response
    console.log(data)

    // 未登录
    if (data.code === 40100) {
      // 不是获取用户信息接口, 或者不是登录页面, 则跳转到登录页面
      if (
        !response.request.responseURL.includes("user/current") &&
        !window.location.pathname.includes("/user/login")
      ) {
        window.location.href = `/user/login?redirect=${window.location.href}`
      }
    }
    return response
  },
  function (error) {
    return Promise.reject(error)
  }
)

export default myAxios


/* SSE */
eventSource = new EventSource("https://example.com/sse")

// 监听所有事件
eventSource.onmessage = (event) => {
  const data = JSON.parse(event.data)
}

// 错误处理, SSE自动重连
eventSource.onerror = (err) => {
  const data = JSON.parse(event.data)
}

// 监听自定义事件 
eventSource.addEventListener("customEvent", (event) => {
  const data = JSON.parse(event.data)
})

if (eventSource) eventSource.close()



/* socket.io-client */
import { io } from "socket.io-client" 

const socket = io("http://example.com", {
  reconnection: true,
  reconnectionDelay: 1000, 
  reconnectionAttempts: 5,
  transports: ["websocket"],
  timeout: 10000,
  auth: { token: "TOKEN" }
})

// 内置事件监听 
socket.on("connect", () => {})
socket.on("connect_error", (err) => {})
socket.on("disconnect", (reason) => {})

// 发送数据 
socket.emit("customEvent", {})

// 监听数据 
socket.on("customEvent", (data) => {})

// 单次监听 
socket.once("customEvent", (data) => {})

// 删除监听器 
socket.off("customEvent")
