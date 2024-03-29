<#assign isMainPage = springMacroRequestContext.requestUri?contains("/main")>
<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    <#if isMainPage>Add a new message<#else>Message editor</#if>
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"  name="text"
                       value="<#if message??>${message.text}</#if>" placeholder="Enter message"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input class="form-control ${(tagError??)?string('is-invalid','')}" type="text" name="tag"
                       value="<#if message??>${message.tag}</#if>" placeholder="#tag"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input class="custom-file-input" type="file" name="file" id="customFile"/>
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message?? && message.id??>${message.id}</#if>"/>
            <div class="form-group">
                <button class="btn btn-primary" type="submit"><#if isMainPage>Add message<#else>Save message</#if></button>
            </div>
        </form>
    </div>
</div>