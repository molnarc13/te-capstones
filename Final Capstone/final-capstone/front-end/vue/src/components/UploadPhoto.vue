<template>
  <div>
    <vue-dropzone
      id="dropzone"
      class="mt-3"
      v-bind:options="dropzoneOptions"
      v-on:vdropzone-sending="addFormData"
      v-on:vdropzone-success="getSuccess"
      :useCustomDropzoneOptions="true"
    ></vue-dropzone>
  </div>
</template>

<script>
/* eslint-disable */
import vue2Dropzone from "vue2-dropzone";
import "vue2-dropzone/dist/vue2Dropzone.min.css";

export default {
  name: "upload-photo",
  components: {
    vueDropzone: vue2Dropzone,
  },
  data() {
    return {
      //-------------------------------------------------------------------------------------
      // TODO: substitute your actual Cloudinary cloud-name where indicated in the URL
      //-------------------------------------------------------------------------------------
      dropzoneOptions: {
        url: "https://api.cloudinary.com/v1_1/dkhepixjf/image/upload",
        thumbnailWidth: 250,
        thumbnailHeight: 250,
        maxFilesize: 2.0,
        acceptedFiles: ".jpg, .jpeg, .png, .gif",
        uploadMultiple: false,
        addRemoveLinks: true,
        dictDefaultMessage:
          "Drop files here to upload. </br> Alternatively, click to select a file for upload.",
      },
    };
  },

  methods: {
    /******************************************************************************************
     * The addFormData method is called when vdropzone-sending event is fired
     * it adds additional headers to the request
     ******************************************************************************************/
    //--------------------------------------------------------------------------------------------
    // TODO: substitute your actual Cloudinary api-key where indicated in the following code
    // TODO: substitute your actual Cloudinary upload preset where indicated in the following code
    //----------------------------------------------------------------------------==---------------
    addFormData(file, xhr, formData) {
      formData.append("api_key", "395925421569991"); // substitute your api key
      formData.append("upload_preset", "bq0mkaov"); // substitute your upload preset
      formData.append("timestamp", (Date.now() / 1000) | 0);
      formData.append("tags", "vue-app");
    },
    /******************************************************************************************
     * The getSuccess method is called when vdropzone-success event is fired
     ******************************************************************************************/
    getSuccess(file, response) {
      const imgUrl = response.secure_url; // store the url for the uploaded image
      this.$emit("image-upload", imgUrl); // fire custom event with image url in someone cares
    },
  },
};
</script>

<style>
</style>