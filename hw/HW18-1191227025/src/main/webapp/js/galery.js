/**Link to the main page of the gallery.*/
var mainPageLink = $("<a>");
mainPageLink.prop("href", "/galerija");
mainPageLink.html("Main page");

$(document).ready(dowork);
/*******************************************************************************
 * Function doing rendering the page after loading.
 * Using ajax, it fetches all available tags.
 * Renders it as buttons.
 */
function dowork() {
	var title = $("<h1>");
	title.html("Welcome to online gallery!");
	$("body").append(title);

	tagsDiv = $("<div>");
	tagsDiv.addClass("tags");
	$("body").append(tagsDiv);
	thumbnailsDiv = $("<div>").prop("id", "thumbnails");
	$("body").append(thumbnailsDiv);
	divPicture = $("<div>");
	divPicture.addClass("divPicture");
	$("body").append(divPicture);

	$.ajax({
				url : "tags",
				dataType : "json",
				success : function(data) {
					var tags = data;
					var message = $("<p>");
					message.addClass("tags");
					message.html("Clicking on the tags below, thumbnails of the pictures holding choosed tag will show at the"
							+ "end of the page.");
					tagsDiv.append(message);

					if (tags.length == 0) {
						html = "No tags to be shown..."
					} else {
						for (var i = 0; i < tags.length; i++) {
							var link = $("<button>");
							link.addClass("tagLink");
							link.html(htmlEscape(tags[i]));
							tagsDiv.append(link);
						}
					}

					$("button.tagLink").click(tagLinkClicked);
				},
				error : function(xhr, status) {
					console.log("error");
				}
			});
}

/**
 * Function called when clicking one of tag buttons. It fetches all
 * {@link Picture}s with tag clicked.
 * Thumbnails of pictures is displayed at the end of the page.
 * 
 * @param event
 *            that has caused link to be clicked.
 */
function tagLinkClicked(event) {
	var tag = $(this).html();
	html = "";

	$.ajax({
		url : "getTagsPics?tag=" + tag,
		dataType : "json",
		success : function(data) {
			var pics = data;
			thumbnailsDiv.html("");

			if (pics.length == 0) {
				html = "Nema rezultata..."
				thumbnailsDiv.html(html);
				
			} else {
				for (var i = 0; i < pics.length; i++) {
					var img = $("<img>");
					img.addClass("thumb");
					img.prop("alt", htmlEscape(pics[i].name));
					img.prop("src", "thumbnail?name=" + htmlEscape(pics[i].name));

					thumbnailsDiv.append(img);
				}
			}

			$("img.thumb").click(thumbnailImgClicked);
		},
		error : function(xhr, status) {
			console.log("error");
		}
	});

}

/**
 * Function called when clicking one of thumbnails pictures. It fetches clicked
 * {@link Picture}, renders it with it's description and tag words.
 * 
 * @param event
 *            that has caused link to be clicked.
 */
function thumbnailImgClicked(event) {

	$.ajax({
		url : "getPicture?name=" + $(this).prop("alt"),
		dataType : "json",
		success : function(data) {
			var picture = data;
			tagsDiv.html("");
			thumbnailsDiv.html("");
			divPicture.html("");

			if (picture.length == 0) {
				html = "Nema rezultata..."
				thumbnailsDiv.html(html);
			} else {

				var paragraf = $("<p>");
				paragraf.addClass("img");
				html = htmlEscape(picture.desc.trim())+"<div class=tags>";
				for (var i = 0; i < picture.tags.length; ++i) {
					html += "<button class=tagLink>"
							+ htmlEscape(htmlEscape(picture.tags[i]).trim()) + "</button>";
				}
				html += "</div></br>";
				paragraf.html(html);
				divPicture.append(paragraf);

				var img = $("<img>");
				img.prop("alt", htmlEscape(picture.name));
				img.prop("src", "picture?name=" + htmlEscape(picture.name));

				divPicture.append(img);
				divPicture.append("<br>");
				divPicture.append(mainPageLink);

				$("button.tagLink").click(tagLinkClicked);
			}
		},
		error : function() {
			console.log("error");
		}
	});
}

/**
 * Function escaping the content of json data gotten trough ajax. It is used to
 * prevent injection attacks.
 * 
 * @param str
 *            html string to be escaped
 * 
 */
function htmlEscape(str) {
	return String(str).replace(/&/g, '&amp;').replace(/"/g, '&quot;').replace(
			/'/g, '&#39;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}
