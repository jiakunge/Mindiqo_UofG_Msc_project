<template>
  <div class="el-tiptap">
    <el-tiptap height="84vh" v-model="editorContent" placeholder="Input something to start" @onUpdate="onEditorUpdate" :extensions="extensions" output="html">
    </el-tiptap>
  </div>
</template>
  
<script>
import {
  // necessary extensions
  Doc,
  Text,
  Paragraph,
  Heading,
  FontSize,
  FontType,
  TextColor,
  TextHighlight,
  Bold,
  Underline,
  Italic,
  Strike,
  ListItem,
  BulletList,
  OrderedList,
  Code,
  CodeBlock,
  Blockquote,
  Link,
  Image,
  Iframe,
  TextAlign,
  History,
} from 'element-tiptap';


export default {
  props: {
    content: {
      type: [String, Object],
      default: '',
    }
  },
  data() {
    return {
      extensions: [
        new Doc(),
        new Text(),
        new Paragraph(),
        new FontSize({
          fontSizes: ['8', '10', '12', '14', '16', '18', '20', '24', '30', '36', '48', '60', '72'],
          bubble: true
        }
        ),
        new FontType({ bubble: true }),
        new TextColor({
          colors: ['#f44336',
            '#e91e63',
            '#9c27b0',
            '#673ab7',
            '#3f51b5',
            '#2196f3',
            '#03a9f4',
            '#00bcd4',
            '#009688',
            '#4caf50',
            '#8bc34a',
            '#cddc39',
            '#ffeb3b',
            '#ffc107',
            '#ff9800',
            '#ff5722',
            '#000000',],
          bubble: true
        },),
        new TextHighlight({
          colors: ['#f44336',
            '#e91e63',
            '#9c27b0',
            '#673ab7',
            '#3f51b5',
            '#2196f3',
            '#03a9f4',
            '#00bcd4',
            '#009688',
            '#4caf50',
            '#8bc34a',
            '#cddc39',
            '#ffeb3b',
            '#ffc107',
            '#ff9800',
            '#ff5722',
            '#000000',],
          bubble: true, menubar: false
        }),
        new Heading({ level: 5 }),
        new Bold({ bubble: true, menubar: false }), // render command-button in bubble menu.
        new Underline({ bubble: true, menubar: false }), // render command-button in bubble menu but not in menubar.
        new Italic({ bubble: true, menubar: false }),
        new Strike({ bubble: true, menubar: false }),
        new ListItem(),
        new BulletList(),
        new OrderedList(),
        new Code({ bubble: true }),
        new CodeBlock({ bubble: true }),
        new Blockquote({ bubble: true }),
        new Link({ bubble: true }),
        new Image(),
        new Iframe(),
        new TextAlign({
          alignments: ['left', 'center', 'right', 'justify'],
          bubble: true
        }),
        new History(),
      ],
      editorContent: this.content,
    };
  },

  watch: {
    content(newVal) {
      // verify if the new value is a Json string
      // if (typeof newVal === 'string') {
      //   try {
      //     newVal = JSON.parse(newVal);
      //   } catch (e) {
      //     console.error('Failed to parse content:', e);
      //   }
      // }

      this.editorContent = newVal;
    },
  },
  methods: {
    onEditorUpdate() {
      this.$emit('update:content', this.editorContent);
    }
  },

};
</script>
<style >
.el-tiptap-editor__content {
  /* max-height: 465px; */
  overflow-y: auto;
  max-width: 900px;
  width: 100%;
  margin: 0px auto;
  background-color: rgb(255, 255, 255);
}

@media (max-width: 768px) {
  .el-tiptap-editor__content {
        max-width: 700px;
    }
}

@media (max-width: 576px) {
  .el-tiptap-editor__content {
        max-width: 500px;
    }
}


.el-tiptap-editor {
  background-color: rgb(245, 245, 245);
}

/* cancel radius */
.el-tiptap-editor .border-top-radius {
  border-top-left-radius: 0px;
  border-top-right-radius: 0px;
}

.el-tiptap-editor .border-bottom-radius {
  border-bottom-left-radius: 0px;
  border-bottom-right-radius: 0px;
}
</style>