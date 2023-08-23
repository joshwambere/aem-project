$(document).ready(function() {
    var typeSelector = $('[name="./type"]');
    var fullVideoPath = $('[name="./FullVideoPath"]');
    var sizedContainer = $('[name="./sizedContainer"]');
    var inline = $('[name="./inline"]');

    // Function to show or hide pathfields based on selected option
    function togglePathfields() {
        var selectedType = typeSelector.val();

        if (selectedType === "FULL_WINDOW") {
            fullVideoPath.show();
            sizedContainer.hide();
            inline.hide();
        } else if (selectedType === "SIZED_CONTAINER") {
            fullVideoPath.hide();
            sizedContainer.show();
            inline.hide();
        } else if (selectedType === "IN_LINE") {
            fullVideoPath.hide();
            sizedContainer.hide();
            inline.show();
        }
    }

    // Initial toggle based on selected value
    togglePathfields();

    // Attach event listener to type selector
    typeSelector.on("change", togglePathfields);
});