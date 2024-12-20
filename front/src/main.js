import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import ElementPlus from "element-plus";  // element-plus 임포트
import "element-plus/dist/index.css";     // 스타일 임포트

const app = createApp(App);

app.use(router);
app.use(ElementPlus);  // ElementPlus 사용

app.mount("#app");
