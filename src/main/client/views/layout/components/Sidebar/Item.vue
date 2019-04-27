<script>
import 'font-awesome/css/font-awesome.css'
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    meta: {
      type: Object,
      default: () => {
        return {
          title: '',
          icon: ''
        }
      }
    }
  },
  render(h, context) {
    const { icon, title } = context.props.meta
    const vnodes = []

    if (icon) {
      const img = h(
        'i',
        {
          'attrs': {
            'class': 'icon-pd fa fa-' + icon
          }
        }
      )
      vnodes.push(img)
    }

    if (title) {
      vnodes.push(<span slot='title'>{(title)}</span>)
    }
    return vnodes
  }
}
</script>

<style rel="stylesheet/css" lang="css" scoped>
  .icon-pd {
		padding-right: 10px;
		font-size: 15px;
  }
</style>
