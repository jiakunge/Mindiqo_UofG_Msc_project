<template>
  <div class="bg">
    <div class="container d-flex justify-content-center">
      <div class="frosted-glass rounded border p-3 mt-5" style="width: 400px;">
        <img src="@/assets/logo_black.png" class="w-75 mb-3 d-inline-block align-top" alt="mindiqo">
        <b-form @submit.stop.prevent="onSubmit">
          <b-form-group id="input-group-1">
            <div class="form-floating mb-3">
              <b-form-input id="input-1" v-model="form.username" placeholder="Username" required></b-form-input>
              <label for="input-1">Username</label>
            </div>
          </b-form-group>

          <b-form-group id="input-group-2">
            <div class="form-floating mb-3">
              <b-form-input id="input-2" type="password" v-model="form.password" placeholder="Password"
                required></b-form-input>
              <label for="input-2">Password</label>
            </div>
          </b-form-group>
          <div class="mb-3">
            <router-link to="/forgot_password" class="text-decoration-none">Forgot password?</router-link>
          </div>
          <div class="mb-3">
            Do not have an account?
            <router-link to="/register" class="text-decoration-none">Register</router-link>
            now
          </div>
          <b-button type="submit" variant="primary">Login</b-button>
        </b-form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      form: {
        username: null,
        password: null,
      }
    };
  },
  methods: {
    onSubmit() {
      var _this = this;

      this.axios.post("/login", {
        username: this.form.username,
        password: this.form.password,
      })
        .then(function (response) {
          if (response.data.code === 1) {
            const userToken = response.data.data;
            localStorage.setItem('userToken', userToken);
            _this.$store.dispatch('loadUserFromToken', userToken);
            _this.$router.push({ path: '/' });
          } else {
            alert("Invalid username and password!")
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },

  },

};
</script>
<style scoped>
.bg {
  background-image: url("@/assets/bg.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed;
  min-height: 100vh;
  width: 100%;
}

.frosted-glass {
  background-color: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(7px);
}



/**
 * This includes code from the Bootstrap project
 * available at https://github.com/twbs/bootstrap
 *
 * Bootstrap is distributed under the terms of the MIT License
 */

.form-floating {
  position: relative;
}

.form-floating>.form-control,
.form-floating>.form-control-plaintext,
.form-floating>.form-select {
  height: calc(3.5rem + calc(var(--bs-border-width) * 2));
  min-height: calc(3.5rem + calc(var(--bs-border-width) * 2));
  line-height: 1.25;
}

.form-floating>label {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 2;
  height: 100%;
  padding: 1rem 0.75rem;
  overflow: hidden;
  text-align: start;
  text-overflow: ellipsis;
  white-space: nowrap;
  pointer-events: none;
  border: var(--bs-border-width) solid transparent;
  transform-origin: 0 0;
  transition: opacity 0.1s ease-in-out, transform 0.1s ease-in-out;
}

@media (prefers-reduced-motion: reduce) {
  .form-floating>label {
    transition: none;
  }
}

.form-floating>.form-control,
.form-floating>.form-control-plaintext {
  padding: 1rem 0.75rem;
}

.form-floating>.form-control::-moz-placeholder,
.form-floating>.form-control-plaintext::-moz-placeholder {
  color: transparent;
}

.form-floating>.form-control::placeholder,
.form-floating>.form-control-plaintext::placeholder {
  color: transparent;
}

.form-floating>.form-control:not(:-moz-placeholder-shown),
.form-floating>.form-control-plaintext:not(:-moz-placeholder-shown) {
  padding-top: 1.625rem;
  padding-bottom: 0.625rem;
}

.form-floating>.form-control:focus,
.form-floating>.form-control:not(:placeholder-shown),
.form-floating>.form-control-plaintext:focus,
.form-floating>.form-control-plaintext:not(:placeholder-shown) {
  padding-top: 1.625rem;
  padding-bottom: 0.625rem;
}

.form-floating>.form-control:-webkit-autofill,
.form-floating>.form-control-plaintext:-webkit-autofill {
  padding-top: 1.625rem;
  padding-bottom: 0.625rem;
}

.form-floating>.form-select {
  padding-top: 1.625rem;
  padding-bottom: 0.625rem;
}

.form-floating>.form-control:not(:-moz-placeholder-shown)~label {
  color: rgba(var(--bs-body-color-rgb), 0.65);
  transform: scale(0.85) translateY(-0.5rem) translateX(0.15rem);
}

.form-floating>.form-control:focus~label,
.form-floating>.form-control:not(:placeholder-shown)~label,
.form-floating>.form-control-plaintext~label,
.form-floating>.form-select~label {
  color: rgba(var(--bs-body-color-rgb), 0.65);
  transform: scale(0.85) translateY(-0.5rem) translateX(0.15rem);
}

.form-floating>.form-control:not(:-moz-placeholder-shown)~label::after {
  position: absolute;
  inset: 1rem 0.375rem;
  z-index: -1;
  height: 1.5em;
  content: "";
  background-color: var(--bs-body-bg);
  border-radius: var(--bs-border-radius);
}

.form-floating>.form-control:focus~label::after,
.form-floating>.form-control:not(:placeholder-shown)~label::after,
.form-floating>.form-control-plaintext~label::after,
.form-floating>.form-select~label::after {
  position: absolute;
  inset: 1rem 0.375rem;
  z-index: -1;
  height: 1.5em;
  content: "";
  background-color: var(--bs-body-bg);
  border-radius: var(--bs-border-radius);
}

.form-floating>.form-control:-webkit-autofill~label {
  color: rgba(var(--bs-body-color-rgb), 0.65);
  transform: scale(0.85) translateY(-0.5rem) translateX(0.15rem);
}

.form-floating>.form-control-plaintext~label {
  border-width: var(--bs-border-width) 0;
}

.form-floating> :disabled~label {
  color: #6c757d;
}

.form-floating> :disabled~label::after {
  background-color: var(--bs-secondary-bg);
}

.input-group {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  align-items: stretch;
  width: 100%;
}

.input-group>.form-control,
.input-group>.form-select,
.input-group>.form-floating {
  position: relative;
  flex: 1 1 auto;
  width: 1%;
  min-width: 0;
}

.input-group>.form-control:focus,
.input-group>.form-select:focus,
.input-group>.form-floating:focus-within {
  z-index: 5;
}</style>