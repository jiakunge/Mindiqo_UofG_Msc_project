import Vue from "vue";
import VueRouter from "vue-router";
//import HomeView from '../views/HomeView.vue'
import RegisterComponent from "../views/register/RegisterView.vue";
import LoginComponent from "../views/login/LoginView.vue";
import HomeComponent from "../views/home/HomeView.vue";
import IndexView from "../views/IndexView.vue";
import MyNotes from "../views/my_note/MyNotesView.vue";
import EditView from "../views/my_note/edit/EditView.vue";
import PostView from "../views/home/post/PostView.vue";
import UserView from "../views/user/UserView.vue";
import SearchView from "../views/search/SearchView.vue";
import NotificationView from "../views/notification/NotificationView.vue";
import store from "../store/store.js";
Vue.use(VueRouter);

const routes = [
  // {
  //   path: '/',
  //   name: 'home',
  //   component: HomeView
  // },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
  {
    path: "/register",
    name: "register",
    component: RegisterComponent,
  },
  {
    path: "/login",
    name: "login",
    component: LoginComponent,
  },
  {
    path: "/",
    name: "Index",
    component: IndexView,
    redirect: "/home",
    children: [
      {
        path: "/home",
        name: "Home",
        component: HomeComponent,
      },
      {
        path: "/home/post/:id",
        name: "Post",
        component: PostView,

      },
      {
        path: "/my_notes",
        name: "MyNotes",
        component: MyNotes,
        meta:{requiresAuth:true}

      },
      {
        path: "/my_notes/edit/:id",
        name: "Edit",
        component: EditView,
        meta:{requiresAuth:true}
      },
      {
        path: "/user/:username",
        name: "User",
        component: UserView,
        meta:{requiresAuth:true}
      },
      {
        path: '/user',
        redirect: () => {
          const username = store.state.userData.username;
        
          if (username) {
            return `/user/${username}`;
          } else {
            return '/login';
          }
        }
      },
      {
        path: '/search',
        name: 'Search',
        component: SearchView,
        props: (route) => ({ ...route.query }) // 将所有的 query 参数转为 props
      },
      {
        path: '/notification',
        name: 'Notification',
        component: NotificationView,
        meta:{requiresAuth:true}

      }

    ],
  },
];

const router = new VueRouter({
  routes,
});

export default router;
