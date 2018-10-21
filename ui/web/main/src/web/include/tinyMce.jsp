<%@ include file="taglibs.jsp" %>

<c:url var="tinyMceUrl" value="/tinymce/js/tinymce/tinymce.min.js" />
<script type="text/javascript" src="${tinyMceUrl}"></script>
<c:url var="jQueryTinyMceUrl" value="/tinymce/js/tinymce/jquery.tinymce.min.js" />
<script type="text/javascript" src="${jQueryTinyMceUrl}"></script>
<script type="text/javascript">
    var globalTinyMceProperties = {
        convert_urls : false,
        menubar : false,
        resize : "both",
        plugins : "preview fullscreen textcolor paste searchreplace link anchor code table charmap image media directionality codesample",
        toolbar: [
            "preview fullscreen | removeformat | bold italic underline strikethrough | subscript superscript | forecolor backcolor | alignleft aligncenter alignright alignjustify",
            "undo redo | cut copy paste pastetext | searchreplace | bullist numlist | advhr | outdent indent | link unlink anchor | ltr rtl",
            "styleselect fontselect fontsizeselect | table | charmap image media | codesample | code"
        ],
        extended_valid_elements : "script[charset|defer|language|src|type]"
    };
</script>
