<#include "security.ftl">
<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-5">
                <input class="form-control ${(usernameError??)?string('is-invalid','')}" type="text" name="username" value="<#if user??>${user.username}</#if>" placeholder="User name"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-5">
                <input class="form-control ${(passwordError??)?string('is-invalid','')}" type="password" name="password" placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Confirm password:</label>
                <div class="col-sm-5">
                    <input class="form-control ${(password2Error??)?string('is-invalid','')}" type="password" name="password2" placeholder="Confirm password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-5">
                    <input class="form-control ${(emailError??)?string('is-invalid','')}" type="email" name="email" value="<#if user??>${user.email}</#if>" placeholder="some@example.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-sm-5 mb-3">
                <div class="g-recaptcha" data-sitekey="6LfjHrcUAAAAAD3RsaHSsH8rRPUqPjVpJsuDEiPE"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        <#else>
            <a href="/registration">Add new user</a>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else>Sign In</#if></button>
    </form>
</#macro>
<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary"><#if user??>Sign Out<#else>Log In</#if></button>
    </form>
</#macro>