function throwMonkeball(targetElement, speed) {

    $(targetElement).animate({marginLeft: "+=360px"},
    {
        duration: speed,
        complete: function ()
        {
            targetElement.animate({marginLeft: "-=360px"},
            {
                duration: speed,
                complete: function ()
                {
                    throwMonkeball(targetElement, speed);
                }
            });
        }
    });
}
;