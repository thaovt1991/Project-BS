
$(function () {
    $("#frmProduct").validate({
        onkeyup: function(element) { $(element).valid() },
        onclick: false,
        onfocusout: false,
        rules: {
            price: {
                required: true,
                minlength: 1,
                maxlength: 12
            },
            percentageDiscount: {
                required: true,
                minlength: 1,
                maxlength: 2
            }
        },
        errorLabelContainer: "#message-error-data",
        errorPlacement: function (error) {
            error.appendTo("#message-error-data");
        },
        showErrors: function(errorMap, errorList) {
            if (this.numberOfInvalids() > 0) {
                $("#message-error-data").removeClass("hide");
            } else {
                $("#message-error-data").addClass("hide");
            }
            this.defaultShowErrors();
        },
        messages: {
            price: {
                required: "Vui lòng nhập giá sản phẩm đầy đủ",
                minlength: "Giá sản phẩm tối thiểu 1 ký tự",
                maxlength: "Giá sản phẩm tối đa 12 ký tự"
            },
            percentageDiscount: {
                required: "Vui lòng nhập mức giảm giá đầy đủ",
                minlength: "Mức giảm giá tối thiểu 1 ký tự từ 1 - 99 %",
                maxlength: "Muc giảm giá vượt quá chỉ tiêu"
            }
        }
    });

});