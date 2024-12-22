import {createRouter, createWebHistory} from 'vue-router';
import LoginPage from "@/pages/LoginPage.vue";
import SignupPage from "@/pages/SignupPage.vue";
import HomePage from "@/pages/HomePage.vue";
import AddWorkPage from "@/pages/AddWorkPage.vue";


const routes = [
  {path: '/login', name: 'login', component: LoginPage},
  {path: '/signup', name: 'signup', component: SignupPage},
  {path: '/',  name: 'home', component: HomePage},
  {path: '/add-work', name: 'addWork', component: AddWorkPage},
  // 기타 라우트 설정
];

const router = createRouter({
  history: createWebHistory(),
  routes
});


router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (!token && to.name !== 'login' && to.name !== 'signup') {
    next({name: 'login'});
  } else {
    next();
  }
});


export default router;
