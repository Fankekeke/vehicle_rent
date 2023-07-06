<template>
  <a-modal v-model="show" title="修改维修" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        修改
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
         <a-col :span="6">
          <a-form-item label='选择维修车辆' v-bind="formItemLayout">
            <a-select disabled v-decorator="[
              'vehicleNo',
              { rules: [{ required: true, message: '请选择维修车辆!' }] }
              ]">
              <a-select-option :value="item.vehicleNo" v-for="(item, index) in vehicleList" :key="index">{{ item.name }} - {{ item.vehicleNumber }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='维修金额' v-bind="formItemLayout">
            <a-input-number style="width: 100%" v-decorator="[
            'price'
            ]" :min="1" :step="1"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='维修原因' v-bind="formItemLayout">
            <a-input v-decorator="[
            'reason',
            { rules: [{ required: true, message: '请输入维修原因!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='办理人' v-bind="formItemLayout">
            <a-input v-decorator="[
            'chargePerson'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='维修开始时间' v-bind="formItemLayout">
            <a-date-picker style="width: 100%" v-decorator="[
            'repairStart',
            { rules: [{ required: true, message: '请输入维修开始时间!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='维修结束时间' v-bind="formItemLayout">
            <a-date-picker style="width: 100%" v-decorator="[
            'repairEnd',
            { rules: [{ required: true, message: '请输入维修结束时间!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='维修店名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'repairShop',
            { rules: [{ required: true, message: '请输入维修店名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label='维修店地址' v-bind="formItemLayout">
            <a-input v-decorator="[
            'shopAddress',
            { rules: [{ required: true, message: '请输入维修店地址!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='备注' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content'
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'repairEdit',
  props: {
    repairEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.repairEditVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      formLoading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      vehicleList: []
    }
  },
  mounted () {
    this.selectVehicleList()
  },
  methods: {
    selectVehicleList () {
      this.$get(`/cos/vehicle-info/list`).then((r) => {
        this.vehicleList = r.data.data
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    responsibleInit (responsible) {
      this.formLoading = false
      if (responsible !== null && responsible !== '') {
        let responsibleList = []
        responsible.split(',').forEach((id, index) => {
          responsibleList.push(parseInt(id))
        })
        this.checkedList = responsibleList
        this.onChange(this.checkedList)
      }
      setTimeout(() => {
        this.formLoading = true
      }, 200)
    },
    setFormValues ({...repair}) {
      this.rowId = repair.id
      let fields = ['vehicleNo', 'reason', 'price', 'chargePerson', 'repairStart', 'repairEnd', 'repairShop', 'shopAddress', 'content']
      let obj = {}
      Object.keys(repair).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(repair['images'])
        }
        if (key === 'repairStart' || key === 'repairEnd') {
          if (repair[key] != null) {
            repair[key] = moment(repair[key])
          }
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = repair[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          if (values.repairStart) {
            values.repairStart = moment(values.repairStart).format('YYYY-MM-DD')
          }
          if (values.repairEnd) {
            values.repairEnd = moment(values.repairEnd).format('YYYY-MM-DD')
          }
          this.loading = true
          this.$put('/cos/vehicle-info/repair/edit', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
