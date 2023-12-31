// Vue core
import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store/store';


/*---------------------------------------------bootstrap----------------------------------------------------------- */
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
/**
 * This file includes code derived from the Bootstrap project
 * available at https://github.com/twbs/bootstrap
 *
 * Bootstrap is distributed under the terms of the MIT License
 */
import 'bootstrap/dist/css/bootstrap.css'
/**
 * This file includes code derived from the Bootstrap-vue project
 * available at https://github.com/bootstrap-vue/bootstrap-vue
 *
 * Bootstrap is distributed under the terms of the MIT License
 */
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
/*---------------------------------------------end bootstrap----------------------------------------------------------- */

/*---------------------------------------------element ui----------------------------------------------------------- */
import ElementUI from 'element-ui';
import { ElementTiptapPlugin } from 'element-tiptap';
// import ElementUI's styles
import 'element-ui/lib/theme-chalk/index.css';
// import this package's styles
import 'element-tiptap/lib/index.css';

// use ElementUI's plugin
Vue.use(ElementUI);
// use this package's plugin
Vue.use(ElementTiptapPlugin, {
  /* plugin options */
    lang: "en", // see i18n
    spellcheck: true, // can be overwritten by editor prop

});
/*---------------------------------------------end element ui----------------------------------------------------------- */


/*---------------------------------------------axios----------------------------------------------------------- */
import axios from 'axios';
import VueAxios from 'vue-axios';
axios.defaults.baseURL = "/api";
axios.defaults.withCredentials = true;
// Add a request interceptor
axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('userToken');
    if (token) {
      config.headers['token'] = token;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
Vue.use(VueAxios,axios);
/*---------------------------------------------end axios----------------------------------------------------------- */


/**---------------------------------------------public method----------------------------------------------------------- */
// notify
Vue.prototype.$notify = function (title, content, variant) {
  this.$bvToast.toast(content, {
    title: title,
    variant: variant,
    solid: true,
    autoHideDelay: 2500,
  })
};

//format date time
import dayjs from 'dayjs';
Vue.prototype.$formatDateTime = function(dateTimeStr) {
  const now = dayjs(); // Current date and time
  const dateObj = dayjs(dateTimeStr); // Convert the date time string to a dayjs object
  const diffInDays = now.diff(dateObj, 'day'); // Calculate the difference in days

  if (diffInDays < 1) {
    // Less than 1 day difference, show only time
    return dateObj.format('HH:mm');
  } else {
    // More than 1 day difference, show date
    return dateObj.format('YYYY-MM-DD');
  }
};
/**----------------------------------------end public method----------------------------------------------------------- */


//router guard
router.beforeEach((to, from, next) => {
  // check if the route requires authentication and user is not logged in
  if (to.matched.some(record => record.meta.requiresAuth)) {
    const userToken = localStorage.getItem('userToken');
    if (!userToken) {
      //  if user is not logged in, redirect to login page.
      next({
        path: '/login',
        query: { redirect: to.fullPath } // Store the full path to redirect the user to after login
      });
    } else {
      // User is logged in, continue to route
      next();
    }
  } else {
    // Doesn't require auth, continue to route
    next();
  }
});

//Vue production tip
Vue.config.productionTip = false;
//Vue instance
new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app');



