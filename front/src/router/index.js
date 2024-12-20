import {createRouter, createWebHistory} from 'vue-router';
import LoginPage from "@/pages/LoginPage.vue";
import SignupPage from "@/pages/SignupPage.vue";
import HomePage from "@/pages/HomePage.vue";
import AddWorkPage from "@/pages/AddWorkPage.vue";


const routes = [
  {path: '/login', name: 'Login', component: LoginPage},
  {path: '/signup',name: 'Signup', component: SignupPage},
  {path: '/', component: HomePage},
  {path: '/add-work', component: AddWorkPage},
  // 기타 라우트 설정
];

const router = createRouter({
  history: createWebHistory(),
  routes
});


router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (!token && to.name !== 'Login' && to.name !== 'Signup') {
    next({name: 'Login'});
  } else {
    next();
  }
});


export default router;
