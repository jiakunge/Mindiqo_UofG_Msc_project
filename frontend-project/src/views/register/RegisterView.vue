<template>
  <div class="bg">
    <div class="container d-flex justify-content-center">
      <div class="frosted-glass rounded border p-3 mt-5" style="width: 400px;">
        <img src="@/assets/logo_black.png" class="w-75 mb-3 d-inline-block align-top" alt="mindiqo">
        <b-form @submit.stop.prevent="onSubmit">
          <b-form-group id="input-group-1">
            <div class="form-floating mb-3">
              <b-form-input id="input-1" type="email" v-model="$v.form.email.$model" placeholder="name@example.com"
                :state="validateState('email')" aria-describedby="input-1-live-feedback"></b-form-input>
              <b-form-invalid-feedback id="input-1-live-feedback">This is a required field and must be a valid
                email.</b-form-invalid-feedback>
              <label for="input-1">Email address</label>
            </div>


          </b-form-group>
          <b-form-group id="input-group-2">
            <div class="form-floating mb-3">
              <b-form-input id="input-2" v-model="$v.form.username.$model" placeholder="Username"
                :state="validateState('username')" aria-describedby="input-2-live-feedback"></b-form-input>
              <b-form-invalid-feedback id="input-2-live-feedback">Username is required, must be 5-12 characters
                long
                and not
                contain spaces, special characters.</b-form-invalid-feedback>
              <label for="input-2">Username</label>
            </div>
          </b-form-group>

          <b-form-group id="input-group-3">
            <div class="form-floating mb-3">
              <b-form-input id="input-3" type="password" v-model="$v.form.password.$model" placeholder="Password"
                :state="validateState('password')" aria-describedby="input-3-live-feedback"></b-form-input>
              <b-form-invalid-feedback id="input-3-live-feedback">Password is required, must be 8-20 characters
                long, contain
                letters and numbers.</b-form-invalid-feedback>
              <label for="input-3">Password</label>
            </div>
          </b-form-group>

          <b-form-group id="input-group-4">
            <div class="form-floating mb-3">
              <b-form-input id="input-4" type="password" v-model="$v.form.confirmPassword.$model"
                placeholder="Confirm Password" :state="validateState('confirmPassword')"
                aria-describedby="input-4-live-feedback"></b-form-input>
              <b-form-invalid-feedback id="input-4-live-feedback">Passwords must match.</b-form-invalid-feedback>
              <label for="input-4">Confirm Password</label>
            </div>
          </b-form-group>


          <b-form-group>
            <div class="mb-3">
              <input type="checkbox" id="terms" v-model="form.agreeToTerms" class="form-check-input" required>

              <label for="terms" class="form-check-label">I accept the
                <router-link to="/terms" class="text-decoration-none">Terms of Use</router-link> &amp;
                <router-link to="/privacy" class="text-decoration-none">Privacy Policy</router-link>
              </label>
            </div>
          </b-form-group>


          <div class="mb-3">
            Already have an account?
            <router-link to="/login" class="text-decoration-none">Login</router-link>
            now
          </div>


          <b-button type="submit" variant="primary">Register</b-button>


        </b-form>

      </div>
    </div>
  </div>
</template>



<script>
import { validationMixin } from "vuelidate";
import { required, minLength, maxLength, email, sameAs, helpers } from "vuelidate/lib/validators";



const usernameRegex = /^[a-zA-Z0-9]*$/;
const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;

export default {
  mixins: [validationMixin],
  data() {
    return {
      form: {
        email: null,
        username: null,
        password: null,
        confirmPassword: null,
        agreeToTerms: false,
      }
    };
  },
  validations: {
    form: {
      email: { required, email },
      username: {
        required,
        minLength: minLength(5),
        maxLength: maxLength(12),
        noSpecialChar: helpers.regex('noSpecialChar', usernameRegex)
      },
      password: {
        required,
        minLength: minLength(8),
        maxLength: maxLength(20),
        strongPassword: helpers.regex('strongPassword', passwordRegex)
      },
      confirmPassword: {
        sameAsPassword: sameAs('password')
      },
    }
  },
  methods: {
    validateState(name) {
      const { $dirty, $error } = this.$v.form[name];
      return $dirty ? !$error : null;
    },
    onSubmit() {
      this.$v.form.$touch();
      if (this.$v.form.$anyError) {
        return;
      }


      this.axios.post("/register", {
        username: this.form.username,
        password: this.form.password,
        email: this.form.username
      }).then(response => {
        if (response.data.code === 1) {
          this.$notify('success', response.data.msg, 'success');
          this.$router.push('/login');
        } else {
          this.$notify('error', response.data.msg, 'danger');
          console.log(response.data.msg);
        }
      })
    }
  }
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
}
</style>